import {
  API_BASE_URL,
  MATCH_BASE_URL,
  ACCESS_TOKEN,
  VOTING_BASE_URL,
  USER_ID
} from "../constants";

const request = options => {
  const headers = new Headers({
    "Content-Type": "application/json"
  });

  if (localStorage.getItem(ACCESS_TOKEN)) {
    headers.append(
      "Authorization",
      "Bearer " + localStorage.getItem(ACCESS_TOKEN)
    );
  }

  const defaults = { headers: headers };
  options = Object.assign({}, defaults, options);

  return fetch(options.url, options).then(response =>
    response.json().then(json => {
      if (!response.ok) {
        return Promise.reject(json);
      }
      return json;
    })
  );
};

const requestUnsecure = options => {
  const headers = new Headers({
    "Content-Type": "application/json"
  });
  const defaults = { headers: headers };
  options = Object.assign({}, defaults, options);

  console.log(options);

  return fetch(options.url, options).then(response => {
    console.log(response);
    return response;
  });
};

export function getCurrentUser() {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return Promise.reject("No access token set.");
  }

  return request({
    url: API_BASE_URL + "/user/me",
    method: "GET"
  });
}

export function getUpcomingMatches() {
  console.log("fetching upcoming matches");
  return fetch(MATCH_BASE_URL + "/upcoming");
}

export function updateMatches() {
  console.log("updating matches");
  return fetch(MATCH_BASE_URL + "/update");
}

export function login(loginRequest) {
  return request({
    url: API_BASE_URL + "/auth/login",
    method: "POST",
    body: JSON.stringify(loginRequest)
  });
}

export function signup(signupRequest) {
  return request({
    url: API_BASE_URL + "/auth/signup",
    method: "POST",
    body: JSON.stringify(signupRequest)
  });
}

export function castVote(voteDetails) {
  return requestUnsecure({
    url: VOTING_BASE_URL + "/add",
    method: "POST",
    body: voteDetails
  });
}

export function getUserVotes() {
  return requestUnsecure({
    url: VOTING_BASE_URL + "/get/" + localStorage.getItem(USER_ID),
    method: "GET"
  });
}
