// export const API_BASE_URL = "http://localhost:8080";
// export const MATCH_BASE_URL = "http://localhost:8181/gullycricket/matches";
// export const VOTING_BASE_URL = "http://localhost:8182/gullycricket/votes";
export const API_BASE_URL = "https://auth-service-dot-gullyipl.appspot.com";
export const MATCH_BASE_URL =
  "https://match-service-dot-gullyipl.appspot.com/gullycricket/matches/";
export const VOTING_BASE_URL =
  "https://voting-service-dot-gullyipl.appspot.com/gullycricket/votes";

export const ACCESS_TOKEN = "accessToken";
export const USER_ID = "userId";

//export const OAUTH2_REDIRECT_URI = "http://localhost:3000/oauth2/redirect";
export const OAUTH2_REDIRECT_URI =
  "http://www.gully-cricket.tk/oauth2/redirect";

export const GOOGLE_AUTH_URL =
  API_BASE_URL + "/oauth2/authorize/google?redirect_uri=" + OAUTH2_REDIRECT_URI;
export const FACEBOOK_AUTH_URL =
  API_BASE_URL +
  "/oauth2/authorize/facebook?redirect_uri=" +
  OAUTH2_REDIRECT_URI;
export const GITHUB_AUTH_URL =
  API_BASE_URL + "/oauth2/authorize/github?redirect_uri=" + OAUTH2_REDIRECT_URI;
