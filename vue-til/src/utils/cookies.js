function saveAuthToCookie(value) {
  document.cookie = `til_auth=${value}`;
}

function saveUserToCookie(value) {
  document.cookie = `til_user=${value}`;
}

function getAuthFromCookie() {
  return document.cookie.replace(
    /(?:(?:^|.*;\s*)til_auth\s*=\s*([^;]*).*$)|^.*$/,
    '$1',
  );
}

function getUserFromCookie() {
  return document.cookie.replace(
    /(?:(?:^|.*;\s*)til_user\s*=\s*([^;]*).*$)|^.*$/,
    '$1',
  );
}

function saveFactoriesToCookie(value) {
  document.cookie = `til_factories=${value}`;
}

function saveDeptsToCookie(value) {
  document.cookie = `til_depts=${value}`;
}

function getFactoriesFromCookie() {
  const value = document.cookie.replace(
    /(?:(?:^|.*;\s*)til_factories\s*=\s*([^;]*).*$)|^.*$/,
    '$1',
  );
  if (value) {
    return JSON.parse(value);
  }
  return value;
}

function getDeptsFromCookie() {
  const value = document.cookie.replace(
    /(?:(?:^|.*;\s*)til_depts\s*=\s*([^;]*).*$)|^.*$/,
    '$1',
  );
  if (value) {
    return JSON.parse(value);
  }
  return value;
}

function saveFactoryToCookie(value) {
  document.cookie = `til_factory=${value}`;
}

function saveDeptToCookie(value) {
  document.cookie = `til_dept=${value}`;
}

function getFactoryFromCookie() {
  const value = document.cookie.replace(
    /(?:(?:^|.*;\s*)til_factory\s*=\s*([^;]*).*$)|^.*$/,
    '$1',
  );
  return value ? parseInt(value) : value;
}

function getDeptFromCookie() {
  const value = document.cookie.replace(
    /(?:(?:^|.*;\s*)til_dept\s*=\s*([^;]*).*$)|^.*$/,
    '$1',
  );
  return value ? parseInt(value) : value;
}

function deleteCookie(value) {
  document.cookie = `${value}=; expires=Thu, 01 Jan 1970 00:00:01 GMT;`;
}

function deleteAllCookies() {
  const cookies = document.cookie.split(';');

  for (let i = 0; i < cookies.length; i++) {
    const cookie = cookies[i];
    const eqPos = cookie.indexOf('=');
    const name = eqPos > -1 ? cookie.substring(0, eqPos) : cookie;
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
  }
}

export {
  saveAuthToCookie,
  saveUserToCookie,
  getAuthFromCookie,
  getUserFromCookie,
  saveFactoriesToCookie,
  saveDeptsToCookie,
  getFactoriesFromCookie,
  getDeptsFromCookie,
  saveFactoryToCookie,
  saveDeptToCookie,
  getFactoryFromCookie,
  getDeptFromCookie,
  deleteCookie,
  deleteAllCookies,
};
