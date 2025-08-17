import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import Owner_App from './Onwer_App'
import './assets/sass/style.scss'
import './assets/sass/section/menu_detail.scss'
import './assets/sass/section/onboarding.scss'
import './assets/sass/section/login.scss'
import './assets/sass/section/signup.scss'
import './assets/sass/section/store_info.scss'
import './assets/sass/section/Owner_menu_language.scss'
import './assets/sass/section/Owner_QR.scss'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
    <Owner_App/>
  </React.StrictMode>
);

