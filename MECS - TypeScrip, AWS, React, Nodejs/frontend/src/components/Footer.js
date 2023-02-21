import React from 'react';
import '../css/Footer.css';
import logo from '../images/logo-white.png';

function Footer() {
  return (
    <div className='footer-div'>
        <footer className='fixed-footer'>
            <div class="content">
                <img className="logo" src={logo} style={{ height: '1.6em', marginBottom: '2vh', opacity: '35%'}} alt="PeterMac App"></img>
                <div className='copyright' style={{ marginBottom:'1vh'}}>
                    <span>
                        © Peter MacCallum Web Application {new Date().getFullYear()}
                    </span>
                </div>
                <p>The aim of the Peter MacCallum project team 
                is to provide a mobile-responsive, working web 
                application with a user-friendly and modern aesthetic 
                that is available for healthcare professionals. </p>
            </div>
            
        </footer>
    </div>
    
  )
}

export default Footer