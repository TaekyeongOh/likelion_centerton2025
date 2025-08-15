import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import Owner_App from './Onwer_App'
import './assets/sass/style.scss'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
    <Owner_App/>
  </React.StrictMode>
);

