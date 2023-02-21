import React from 'react';
import Navbar from './components/Navbar';
import DefaultHome from './components/DefaultHome';
import Register from './components/Register';
import Login from './components/Login';
import LandingPage from './components/LandingPage/LandingPage';
import Files from './components/Files';
import AccountDetails from './components/AccountDetails';
import {BrowserRouter as Router,Routes,Route} from 'react-router-dom';
import './css/Button.css';
import './css/App.css';
import Footer from './components/Footer';

function App() {
  return (
      <Router>
        <div className="App">
          <Navbar/>
          <div className="main-content">
            <Routes>
            <Route exact path="/" element= {<DefaultHome/>}/>
            <Route exact path="/landing" element= {<LandingPage/>}/>
            <Route path="/myfiles" element= {<Files/>}/>
            <Route path='/accountDetails' element= {<AccountDetails/>}/>
            <Route path="/login" element= {<Login/>}/>
            <Route path="/register" element= {<Register/>}/>
            </Routes>
          </div>
          <Footer/>
        </div>
      </Router>
  );
}

export default App;
