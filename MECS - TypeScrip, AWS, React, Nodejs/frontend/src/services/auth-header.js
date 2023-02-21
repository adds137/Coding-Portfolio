export default function authHeader() {
  const user = JSON.parse(localStorage.getItem('user'));
  if (user) {
      return { headers: {Authorization: 'Bearer ' + user } };
  } else {
      return {};
  }
}
// may use this for accessing data from api