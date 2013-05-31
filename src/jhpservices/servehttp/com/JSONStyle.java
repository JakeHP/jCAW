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
import jhpservices.servehttp.com.JStylerObj.MustProtect;
import jhpservices.servehttp.com.JStylerObj.StringProtector;

/**
 * JSONStyle object configure JSonSerializer reducing output size
 * 
 * @author Uriel Chemouni <uchemouni@gmail.com>
 */
public class JSONStyle {
	/**
	 * for advanced usage sample see
	 * 
	 * @see net.minidev.json.test.TestCompressorFlags
	 */
	public final static int FLAG_PROTECT_KEYS = 1;
	public final static int FLAG_PROTECT_4WEB = 2;
	public final static int FLAG_PROTECT_VALUES = 4;
	/**
	 * AGRESSIVE have no effect without PROTECT_KEYS or PROTECT_VALUE
	 * 
	 * AGRESSIVE mode allows §Json-smart to not protect String containing
	 * special chars
	 */
	public final static int FLAG_AGRESSIVE = 8;

	public final static JSONStyle NO_COMPRESS = new JSONStyle();
	public final static JSONStyle MAX_COMPRESS = new JSONStyle(-1);
	/**
	 * @since 1.0.9.1
	 */
	public final static JSONStyle LT_COMPRESS = new JSONStyle(FLAG_PROTECT_4WEB);

	private boolean _protectKeys;
	private boolean _protect4Web;
	private boolean _protectValues;

	private MustProtect mpKey;
	private MustProtect mpValue;

	private StringProtector esc;

	public JSONStyle(int FLAG) {
		_protectKeys = (FLAG & FLAG_PROTECT_KEYS) == 0;
		_protectValues = (FLAG & FLAG_PROTECT_VALUES) == 0;
		_protect4Web = (FLAG & FLAG_PROTECT_4WEB) == 0;

		MustProtect mp;
		if ((FLAG & FLAG_AGRESSIVE) > 0)
			mp = JStylerObj.MP_AGGRESIVE;
		else
			mp = JStylerObj.MP_SIMPLE;

		if (_protectValues)
			mpValue = JStylerObj.MP_TRUE;
		else
			mpValue = mp;

		if (_protectKeys)
			mpKey = JStylerObj.MP_TRUE;
		else
			mpKey = mp;

		if (_protect4Web)
			esc = JStylerObj.ESCAPE4Web;
		else
			esc = JStylerObj.ESCAPE_LT;
	}

	public JSONStyle() {
		this(0);
	}

	public boolean protectKeys() {
		return _protectKeys;
	}

	public boolean protectValues() {
		return _protectValues;
	}

	public boolean protect4Web() {
		return _protect4Web;
	}

	public boolean indent() {
		return false;
	}

	public boolean mustProtectKey(String s) {
		return mpKey.mustBeProtect(s);
	}

	public boolean mustProtectValue(String s) {
		return mpValue.mustBeProtect(s);
	}

	public void escape(String s, Appendable out) {
		esc.escape(s, out);
	}
}
