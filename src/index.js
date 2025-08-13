import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import './assets/sass/style.scss'
import './assets/sass/section/menu_detail.scss'
import './assets/sass/section/onboarding.scss'
import './assets/sass/section/login.scss'
import './assets/sass/section/signup.scss'
import './assets/sass/section/store_info.scss'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

