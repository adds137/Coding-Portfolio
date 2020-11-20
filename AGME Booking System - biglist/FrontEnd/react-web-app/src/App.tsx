import React from 'react';
import './App.scss';
import Worker from './components/Worker/Worker';
import BusinessPage2 from './components/Business/BusinessPage2';
import { BrowserRouter as Router,Switch,Route } from "react-router-dom";
import UserHomepage from './components/User/UserHomepage';
import HomePageContent from './components/HomePage/HomePageContent';
import SearchPage from './components/Search/SearchPage'
import EditBusinessPage from './components/Business/EditBusinessPage'
import LoginPage from './components/Entry/LoginPage';
import RegisterPage from './components/Entry/RegisterPage';
import {Provider} from "react-redux";
import store from './store';
import EditUser from './components/User/EditUser';
import ContactPage from './components/Contact/ContactPage';
import Booking from './components/Booking/Booking'
import NoMatch from './components/NoMatch/NoMatch';
import NoAuth from './components/NoMatch/NoAuth';

function App() {
  return (
    <div>
      <Provider store={store}>
        <Router>
          <Switch>
            <Route exact path="/" component={HomePageContent} />
            <Route path="/Worker" component={Worker} />
            <Route exact path='/UserHomepage/:username' component={UserHomepage}/>
            <Route path = "/UserHomepage/EditUser/:username" render={(props) => <EditUser {...props}/>}/>
            <Route exact path="/BusinessPage/:id" render={(props) => <BusinessPage2 {...props}/>}/>
            <Route path="/BusinessPage/edit/:id" render={(props) => <EditBusinessPage {...props}/>}/>
            <Route path="/Search/:searchid" render={(props) => <SearchPage {...props}/> } />
            <Route path="/Search" component={SearchPage} />
            <Route path="/Contact" component={ContactPage}/> 
            <Route path="/Login" component={LoginPage} />
            <Route path="/Signup" component={RegisterPage} />
            <Route path="/Booking/:id" render={(props) => <Booking {...props}/>} />

            <Route path="/Booking" component={Booking} />
            
            <Route path="/Unauthorized" component={NoAuth} />
            <Route component={NoMatch}/>
          </Switch>
        </Router>
        </Provider>
    </div>
  );
}

export default App;
