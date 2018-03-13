/**
 * ============LICENSE_START=======================================================
 * org.onap.aai
 * ================================================================================
 * Copyright © 2017 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 *
 * ECOMP is a trademark and service mark of AT&T Intellectual Property.
 */
package org.onap.aai.rest.dsl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import org.onap.aai.AAIDslLexer;
import org.onap.aai.AAIDslParser;

import org.onap.aai.rest.dsl.DslListener;
import org.antlr.v4.runtime.Token;

import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFManager;
/**
 * The Class DslQueryProcessor.
 */
public class DslQueryProcessor {

	private static final EELFLogger LOGGER = EELFManager.getInstance().getLogger(DslQueryProcessor.class);

	public String parseAaiQuery(String aaiQuery) {
		try {
			// Create a input stream that reads our string
			InputStream stream = new ByteArrayInputStream(aaiQuery.getBytes(StandardCharsets.UTF_8));

			// Create a lexer from the input CharStream
			AAIDslLexer lexer = new AAIDslLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8));

			// Get a list of tokens pulled from the lexer
			CommonTokenStream tokens = new CommonTokenStream(lexer);

			
			// Parser that feeds off of the tokens buffer
			AAIDslParser parser = new AAIDslParser(tokens);

			// Specify our entry point
			ParseTree ptree = parser.aaiquery();
			LOGGER.info("QUERY-interim" + ptree.toStringTree(parser));

			// Walk it and attach our listener
			ParseTreeWalker walker = new ParseTreeWalker();
			DslListener listener = new DslListener();
			walker.walk(listener, ptree);
			LOGGER.info("Final QUERY" + listener.query);

			/*
			 * TODO - Visitor patternQueryDslVisitor visitor = new
			 * QueryDslVisitor(); String query = visitor.visit(ptree);
			 * 
			 */
			return listener.query;
		} catch (Exception e) {
			LOGGER.error("Error while processing the query"+e.getMessage());
		}
		return "";
	}

	public static class Builder {

		/*
		 * Builder constructor doesnt do anything
		 */
		public Builder() {
			// Do nothing
		}

		public String build(String aaiQuery) {

			return new DslQueryProcessor().parseAaiQuery(aaiQuery);
		}
	}
}