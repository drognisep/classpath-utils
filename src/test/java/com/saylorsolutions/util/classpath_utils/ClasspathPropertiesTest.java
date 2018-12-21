/*******************************************************************************
 * Copyright 2018 Saylor Solutions
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.saylorsolutions.util.classpath_utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.saylorsolutions.util.classpath_utils.ClasspathProperties;

public class ClasspathPropertiesTest {
	private ClasspathProperties props;
	
	@BeforeEach
	public void before() {
		props = new ClasspathProperties("test.properties");
		props.setShowStacktraces(true);
	}
	
	@AfterEach
	public void after() {
		props = null;
	}
	
	@Test
	@DisplayName("Failure to load resource does not return null")
	public void testFailedLoad() {
		props.setShowStacktraces(false);
		props = new ClasspathProperties("some_nonexistant_file");
		assertNotNull(props);
	}
	
	@Test
	@DisplayName("Test loading of String classpath props")
	public void testLoadStringProps() {
		assertEquals("test_prop_1", props.getProperty("test.prop.1"));
		assertEquals("1_prop_test", props.getProperty("1.prop.test"));
		assertEquals("some_prop_test", props.getProperty("some.prop.test"));
	}
	
	@Test
	@DisplayName("Test loading of int and BigInteger classpath props")
	public void testLoadIntegerProps() {
		assertEquals(1010, props.getIntegerProperty("int1"));
		assertEquals(2020, props.getIntegerProperty("int2"));
		props.setShowStacktraces(false);
		assertEquals(0, props.getIntegerProperty("int3"));
		assertEquals(0, props.getIntegerProperty("badint2"));
		props.setShowStacktraces(true);

		assertEquals(new BigInteger("1010"), props.getBigIntegerProperty("int1"));
		assertEquals(new BigInteger("2020"), props.getBigIntegerProperty("int2"));
		props.setShowStacktraces(false);
		assertEquals(BigInteger.ZERO, props.getBigIntegerProperty("int3"));
		assertEquals(BigInteger.ZERO, props.getBigIntegerProperty("badint2"));
		props.setShowStacktraces(true);
	}
	
	@Test
	@DisplayName("Test loading of double and BigDecimal classpath props")
	public void testLoadDecimalProps() {
		assertEquals(0.1, props.getDecimalProperty("dec1"));
		assertEquals(1.9, props.getDecimalProperty("dec2"));
		props.setShowStacktraces(false);
		assertEquals(0.0, props.getDecimalProperty("baddec1"));
		props.setShowStacktraces(true);

		assertEquals(new BigDecimal("0.1"), props.getBigDecimalProperty("dec1"));
		assertEquals(new BigDecimal("1.9"), props.getBigDecimalProperty("dec2"));
		props.setShowStacktraces(false);
		assertEquals(BigDecimal.ZERO, props.getBigDecimalProperty("baddec1"));
		props.setShowStacktraces(true);
	}
}
