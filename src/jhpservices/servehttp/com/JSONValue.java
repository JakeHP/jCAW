package jhpservices.servehttp.com;

/*
 *    Copyright 2011 JSON-SMART authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jhpservices.servehttp.com.parser.ContentHandler;
import jhpservices.servehttp.com.parser.ContentHandlerCompressor;
import jhpservices.servehttp.com.parser.FakeContainerFactory;
import jhpservices.servehttp.com.parser.JSONParser;
import jhpservices.servehttp.com.parser.ParseException;
import static jhpservices.servehttp.com.parser.ContainerFactory.FACTORY_ORDERED;
import static jhpservices.servehttp.com.parser.ContainerFactory.FACTORY_SIMPLE;
import static jhpservices.servehttp.com.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static jhpservices.servehttp.com.parser.JSONParser.MODE_RFC4627;

/**
 * JSONValue is the helper class In most of case you should use those static
 * methode to user JSON-smart
 * 
 * 
 * The most commonly use methode are {@link #parse(String)}
 * {@link #toJSONString(Object)}
 * 
 * @author Uriel Chemouni <uchemouni@gmail.com>
 */
public class JSONValue {
	/**
	 * Global default compression type
	 */
	public static JSONStyle COMPRESSION = JSONStyle.NO_COMPRESS;

	/**
	 * Used for validating Json inputs
	 */
	private final static FakeContainerFactory FACTORY_FAKE_COINTAINER = new FakeContainerFactory();

	/**
	 * Parse JSON text into java object from the input source. Please use
	 * parseWithException() if you don't want to ignore the exception. if you
	 * want strict input check use parseStrict()
	 * 
	 * @see JSONParser#parse(Reader)
	 * @see #parseWithException(Reader)
	 * 
	 * @since 1.0.9-2
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 * 
	 */
	public static Object parse(byte[] in) {
		try {
			return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parse JSON text into java object from the input source. Please use
	 * parseWithException() if you don't want to ignore the exception. if you
	 * want strict input check use parseStrict()
	 * 
	 * @see JSONParser#parse(Reader)
	 * @see #parseWithException(Reader)
	 * 
	 * @since 1.1.2
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 * 
	 */
	public static Object parse(byte[] in, int offset, int length) {
		try {
			return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in, offset, length);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parse JSON text into java object from the input source. Please use
	 * parseWithException() if you don't want to ignore the exception. if you
	 * want strict input check use parseStrict()
	 * 
	 * @see JSONParser#parse(Reader)
	 * @see #parseWithException(Reader)
	 * 
	 * @since 1.0.9-2
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 * 
	 */
	public static Object parse(InputStream in) {
		try {
			return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parse JSON text into java object from the input source. Please use
	 * parseWithException() if you don't want to ignore the exception. if you
	 * want strict input check use parseStrict()
	 * 
	 * @see JSONParser#parse(Reader)
	 * @see #parseWithException(Reader)
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 * 
	 */
	public static Object parse(Reader in) {
		try {
			return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parse JSON text into java object from the input source. Please use
	 * parseWithException() if you don't want to ignore the exception. if you
	 * want strict input check use parseStrict()
	 * 
	 * @see JSONParser#parse(String)
	 * @see #parseWithException(String)
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 * 
	 */
	public static Object parse(String s) {
		try {
			return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(s);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parse Json input to a java Object keeping element order
	 * 
	 * @since 1.0.9-2
	 */
	public static Object parseKeepingOrder(byte[] in) {
		try {
			return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in, FACTORY_ORDERED);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parse Json input to a java Object keeping element order
	 * 
	 * @since 1.1.2
	 */
	public static Object parseKeepingOrder(byte[] in, int offset, int length) {
		try {
			return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in, offset, length, FACTORY_ORDERED);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parse Json input to a java Object keeping element order
	 * 
	 * @since 1.0.9-2
	 */
	public static Object parseKeepingOrder(InputStream in) {
		try {
			return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in, FACTORY_ORDERED);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parse Json input to a java Object keeping element order
	 * 
	 * @since 1.0.6.1
	 */
	public static Object parseKeepingOrder(Reader in) {
		try {
			return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in, FACTORY_ORDERED);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parse Json input to a java Object keeping element order
	 * 
	 * @since 1.0.6.1
	 */
	public static Object parseKeepingOrder(String in) {
		try {
			return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in, FACTORY_ORDERED);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parse Json Using SAX event handler
	 * 
	 * @since 1.0.9-2
	 */
	public static void SAXParse(InputStream input, ContentHandler handler) throws ParseException, IOException {
		JSONParser p = new JSONParser(DEFAULT_PERMISSIVE_MODE);
		p.parse(input, FACTORY_FAKE_COINTAINER, handler);
	}

	/**
	 * Parse Json Using SAX event handler
	 * 
	 * @since 1.0.6.2
	 */
	public static void SAXParse(Reader input, ContentHandler handler) throws ParseException, IOException {
		JSONParser p = new JSONParser(DEFAULT_PERMISSIVE_MODE);
		p.parse(input, FACTORY_FAKE_COINTAINER, handler);
	}

	/**
	 * Parse Json Using SAX event handler
	 * 
	 * @since 1.0.6.2
	 */
	public static void SAXParse(String input, ContentHandler handler) throws ParseException {
		JSONParser p = new JSONParser(DEFAULT_PERMISSIVE_MODE);
		p.parse(input, FACTORY_FAKE_COINTAINER, handler);
	}

	/**
	 * Reformat Json input keeping element order
	 * 
	 * @since 1.0.6.2
	 */
	public static String compress(String input, JSONStyle style) {
		try {
			StringBuilder sb = new StringBuilder();
			ContentHandlerCompressor comp = new ContentHandlerCompressor(sb, style);
			JSONParser p = new JSONParser(DEFAULT_PERMISSIVE_MODE);
			p.parse(input, FACTORY_FAKE_COINTAINER, comp);
			return sb.toString();
		} catch (Exception e) {
			return input;
		}
	}

	/**
	 * Compress Json input keeping element order
	 * 
	 * @since 1.0.6.1
	 */
	public static String compress(String s) {
		return compress(s, JSONStyle.MAX_COMPRESS);
	}

	/**
	 * Compress Json input keeping element order
	 * 
	 * @since 1.0.6.1
	 */
	public static String uncompress(String s) {
		return compress(s, JSONStyle.NO_COMPRESS);
	}

	/**
	 * Parse JSON text into java object from the input source.
	 * 
	 * @see JSONParser
	 * 
	 * @since 1.0.9-2
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 */
	public static Object parseWithException(byte[] in) throws IOException, ParseException {
		return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in, FACTORY_SIMPLE);
	}

	/**
	 * Parse JSON text into java object from the input source.
	 * 
	 * @see JSONParser
	 * 
	 * @since 1.1.2
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 */
	public static Object parseWithException(byte[] in, int offset, int length) throws IOException, ParseException {
		return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in, offset, length, FACTORY_SIMPLE);
	}

	/**
	 * Parse JSON text into java object from the input source.
	 * 
	 * @see JSONParser
	 * 
	 * @since 1.0.9-2
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 */
	public static Object parseWithException(InputStream in) throws IOException, ParseException {
		return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in, FACTORY_SIMPLE);
	}

	/**
	 * Parse JSON text into java object from the input source.
	 * 
	 * @see JSONParser
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 */
	public static Object parseWithException(Reader in) throws IOException, ParseException {
		return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in, FACTORY_SIMPLE);
	}

	/**
	 * Parse JSON text into java object from the input source.
	 * 
	 * @see JSONParser
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 */
	public static Object parseWithException(String s) throws ParseException {
		return new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(s, FACTORY_SIMPLE);
	}

	/**
	 * Parse valid RFC4627 JSON text into java object from the input source.
	 * 
	 * @see JSONParser
	 * 
	 * @since 1.0.9-2
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 */
	public static Object parseStrict(InputStream in) throws IOException, ParseException {
		return new JSONParser(MODE_RFC4627).parse(in, FACTORY_SIMPLE);
	}

	/**
	 * Parse valid RFC4627 JSON text into java object from the input source.
	 * 
	 * @see JSONParser
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 */
	public static Object parseStrict(Reader in) throws IOException, ParseException {
		return new JSONParser(MODE_RFC4627).parse(in, FACTORY_SIMPLE);
	}

	/**
	 * Parse valid RFC4627 JSON text into java object from the input source.
	 * 
	 * @see JSONParser
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 */
	public static Object parseStrict(String s) throws ParseException {
		return new JSONParser(MODE_RFC4627).parse(s, FACTORY_SIMPLE);
	}

	/**
	 * Parse valid RFC4627 JSON text into java object from the input source.
	 * 
	 * @see JSONParser
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 */
	public static Object parseStrict(byte[] s) throws ParseException {
		return new JSONParser(MODE_RFC4627).parse(s, FACTORY_SIMPLE);
	}

	/**
	 * Parse valid RFC4627 JSON text into java object from the input source.
	 * 
	 * @see JSONParser
	 * 
	 * @since 1.1.2
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 */
	public static Object parseStrict(byte[] s, int offset, int length) throws ParseException {
		return new JSONParser(MODE_RFC4627).parse(s, offset, length, FACTORY_SIMPLE);
	}

	/**
	 * Check RFC4627 Json Syntax from input Reader
	 * 
	 * @return if the input is valid
	 */
	public static boolean isValidJsonStrict(Reader in) throws IOException {
		try {
			new JSONParser(MODE_RFC4627).parse(in, FACTORY_FAKE_COINTAINER);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * check RFC4627 Json Syntax from input String
	 * 
	 * @return if the input is valid
	 */
	public static boolean isValidJsonStrict(String s) {
		try {
			new JSONParser(MODE_RFC4627).parse(s, FACTORY_FAKE_COINTAINER);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Check Json Syntax from input Reader
	 * 
	 * @return if the input is valid
	 */
	public static boolean isValidJson(Reader in) throws IOException {
		try {
			new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(in, FACTORY_FAKE_COINTAINER);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Check Json Syntax from input String
	 * 
	 * @return if the input is valid
	 */
	public static boolean isValidJson(String s) {
		try {
			new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(s, FACTORY_FAKE_COINTAINER);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Encode an object into JSON text and write it to out.
	 * <p>
	 * If this object is a Map or a List, and it's also a JSONStreamAware or a
	 * JSONAware, JSONStreamAware or JSONAware will be considered firstly.
	 * <p>
	 * 
	 * @see JSONObject#writeJSON(Map, Appendable)
	 * @see JSONArray#writeJSONString(List, Appendable)
	 */
	public static void writeJSONString(Object value, Appendable out) throws IOException {
		writeJSONString(value, out, COMPRESSION);
	}

	/**
	 * Encode an object into JSON text and write it to out.
	 * <p>
	 * If this object is a Map or a List, and it's also a JSONStreamAware or a
	 * JSONAware, JSONStreamAware or JSONAware will be considered firstly.
	 * <p>
	 * 
	 * @see JSONObject#writeJSON(Map, Appendable)
	 * @see JSONArray#writeJSONString(List, Appendable)
	 */
	@SuppressWarnings("unchecked")
	public static void writeJSONString(Object value, Appendable out, JSONStyle compression) throws IOException {
		if (value == null) {
			out.append("null");
			return;
		}

		if (value instanceof String) {
			if (!compression.mustProtectValue((String) value))
				out.append((String) value);
			else {
				out.append('"');
				escape((String) value, out, compression);
				out.append('"');
			}
			return;
		}

		if (value instanceof Number) {
			if (value instanceof Double) {
				if (((Double) value).isInfinite())
					out.append("null");
				else
					out.append(value.toString());
			} else if (value instanceof Float) {
				if (((Float) value).isInfinite())
					out.append("null");
				else
					out.append(value.toString());
			} else {
				out.append(value.toString());
			}
			return;
		}

		if (value instanceof Boolean) {
			out.append(value.toString());
		} else if ((value instanceof JSONStreamAware)) {
			if (value instanceof JSONStreamAwareEx)
				((JSONStreamAwareEx) value).writeJSONString(out, compression);
			else
				((JSONStreamAware) value).writeJSONString(out);
		} else if ((value instanceof JSONAware)) {
			if ((value instanceof JSONAwareEx))
				out.append(((JSONAwareEx) value).toJSONString(compression));
			else
				out.append(((JSONAware) value).toJSONString());
		} else if (value instanceof Map<?, ?>) {
			JSONObject.writeJSON((Map<String, Object>) value, out, compression);
		} else if (value instanceof Iterable<?>) { // List
			JSONArray.writeJSONString((Iterable<Object>) value, out, compression);
		} else if (value instanceof Date) {
			JSONValue.writeJSONString(value.toString(), out, compression);
		} else if (value instanceof Enum<?>) {
			@SuppressWarnings("rawtypes")
			String s = ((Enum) value).name();
			if (!compression.mustProtectValue(s))
				out.append(s);
			else {
				out.append('"');
				escape(s, out, compression);
				out.append('"');
			}
			return;
		} else if (value.getClass().isArray()) {
			Class<?> arrayClz = value.getClass();
			Class<?> c = arrayClz.getComponentType();

			out.append('[');
			boolean needSep = false;

			if (c.isPrimitive()) {
				if (c == int.class) {
					for (int b : ((int[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						out.append(Integer.toString(b));
					}
				} else if (c == short.class) {
					for (short b : ((short[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						out.append(Short.toString(b));
					}
				} else if (c == byte.class) {
					for (byte b : ((byte[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						out.append(Byte.toString(b));
					}
				} else if (c == long.class) {
					for (long b : ((long[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						out.append(Long.toString(b));
					}
				} else if (c == float.class) {
					for (float b : ((float[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						out.append(Float.toString((float) b));
					}
				} else if (c == double.class) {
					for (double b : ((double[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						out.append(Double.toString((double) b));
					}
				} else if (c == boolean.class) {
					for (boolean b : ((boolean[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						if (b)
							out.append("true");
						else
							out.append("false");
					}
				}
			} else {
				for (Object o : ((Object[]) value)) {
					if (needSep)
						out.append(',');
					else
						needSep = true;
					writeJSONString(o, out, compression);
				}
			}
			out.append(']');
		} else {
			try {
				Class<?> cls = value.getClass();
				boolean needSep = false;
				Field[] fields = cls.getDeclaredFields();
				out.append('{');
				for (Field field : fields) {
					int m = field.getModifiers();
					if ((m & (Modifier.STATIC | Modifier.TRANSIENT | Modifier.FINAL)) > 0)
						continue;
					Object v = null;
					if ((m & Modifier.PUBLIC) > 0) {
						v = field.get(value);
					} else {
						String g = JSONUtil.getGetterName(field.getName());
						Method mtd = null;

						try {
							mtd = cls.getDeclaredMethod(g);
						} catch (Exception e) {
						}
						if (mtd == null) {
							Class<?> c2 = field.getType();
							if (c2 == Boolean.TYPE || c2 == Boolean.class) {
								g = JSONUtil.getIsName(field.getName());
								mtd = cls.getDeclaredMethod(g);
							}
						}
						if (mtd == null)
							continue;
						v = mtd.invoke(value);
					}
					if (needSep)
						out.append(',');
					else
						needSep = true;
					JSONObject.writeJSONKV(field.getName(), v, out, compression);
				}
				out.append('}');
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Encode an object into JSON text and write it to out.
	 * <p>
	 * If this object is a Map or a List, and it's also a JSONStreamAware or a
	 * JSONAware, JSONStreamAware or JSONAware will be considered firstly.
	 * <p>
	 * 
	 * @see JSONObject#writeJSON(Map, Appendable)
	 * @see JSONArray#writeJSONString(List, Appendable)
	 */
	public static String toJSONString(Object value) {
		return toJSONString(value, COMPRESSION);
	}

	/**
	 * Convert an object to JSON text.
	 * <p>
	 * If this object is a Map or a List, and it's also a JSONAware, JSONAware
	 * will be considered firstly.
	 * <p>
	 * DO NOT call this method from toJSONString() of a class that implements
	 * both JSONAware and Map or List with "this" as the parameter, use
	 * JSONObject.toJSONString(Map) or JSONArray.toJSONString(List) instead.
	 * 
	 * @see JSONObject#toJSONString(Map)
	 * @see JSONArray#toJSONString(List)
	 * 
	 * @return JSON text, or "null" if value is null or it's an NaN or an INF
	 *         number.
	 */
	public static String toJSONString(Object value, JSONStyle compression) {
		StringBuilder sb = new StringBuilder();
		try {
			writeJSONString(value, sb, compression);
		} catch (IOException e) {
			// can not append on a StringBuilder
		}
		return sb.toString();
	}

	public static String escape(String s) {
		return escape(s, COMPRESSION);
	}

	/**
	 * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters
	 * (U+0000 through U+001F).
	 */
	public static String escape(String s, JSONStyle compression) {
		if (s == null)
			return null;
		StringBuilder sb = new StringBuilder();
		compression.escape(s, sb);
		return sb.toString();
	}

	public static void escape(String s, Appendable ap) {
		escape(s, ap, COMPRESSION);
	}

	public static void escape(String s, Appendable ap, JSONStyle compression) {
		if (s == null)
			return;
		compression.escape(s, ap);
	}
}
