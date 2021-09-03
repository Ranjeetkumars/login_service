/**
 * 
 */
package com.pro.login.utills;

/**
 * @author VENKAT_PRO
 *
 */
public enum LoginResult {
	MAX_LIMIT_LOGIN_EXCEEDED() {
		@Override
		public String getLoginResult() {
			return "Max Limit For Login Exceeded";
		}

		@Override
		public Integer getErrorcode() {
			return 1;
		}
	},
	USERNOT_FOUND() {
		@Override
		public String getLoginResult() {
			return "User Not Found";
		}

		@Override
		public Integer getErrorcode() {
			return 2;
		}
	},
	PASSWORDNOTFOUND() {
		@Override
		public String getLoginResult() {
			return "Password do not match";
		}

		@Override
		public Integer getErrorcode() {
			return 3;
		}
	},
	MODULE_NOT_FOUND() {
		@Override
		public String getLoginResult() {
			return "Module Not Found";
		}

		@Override
		public Integer getErrorcode() {
			return 4;
		}
	},
	ROLE_NOT_FOUND() {
		@Override
		public String getLoginResult() {
			return "Role Not Found";
		}

		@Override
		public Integer getErrorcode() {
			return 5;
		}
	},
	CTI_NOT_MAPPED() {
		@Override
		public String getLoginResult() {
			return "CTI Credentials NotMapped";
		}

		@Override
		public Integer getErrorcode() {
			return 6;
		}
	},
	TERMINAL_NOT_MAPPED() {
		@Override
		public String getLoginResult() {
			return "Terminal NotMapped";
		}

		@Override
		public Integer getErrorcode() {
			return 7;
		}
	},
	USER_IS_ALLREADY_LOGIN() {
		@Override
		public String getLoginResult() {
			return "User Is Allready Login With Another System";
		}

		@Override
		public Integer getErrorcode() {
			return 8;
		}
	},
	USER_IS_LOGIN_WITH_OTHERMODULE() {
		@Override
		public String getLoginResult() {
			return "User Is Allready login With Another Module";
		}

		@Override
		public Integer getErrorcode() {
			return 9;
		}
	};
	public abstract String getLoginResult();

	public abstract Integer getErrorcode();
}
