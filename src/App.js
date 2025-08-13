import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Main from './components/Customer_Section/Main'
import Onboarding from './components/Owner_Section/Onboarding'
import Login from './components/Owner_Section/Login'
import Signup from './components/Owner_Section/Signup'
import Storeinfo from './components/Owner_Section/Store_info'
import Menu1 from './components/Customer_Section/Menu1'
import Menu2 from './components/Customer_Section/Menu2'
import Menu3 from './components/Customer_Section/Menu3'
import Menu4 from './components/Customer_Section/Menu4'
import Menu5 from './components/Customer_Section/Menu5'
import QRScan from './components/Customer_Section/QRScan'

const App = () => {
  return (
    <BrowserRouter>
    <Routes>
      <Route path='/' element={<Main />} />
      <Route path='/Onboarding' element={<Onboarding/>} />
      <Route path='/Login' element={<Login/>} />
      <Route path='/Signup' element={<Signup/>} />
      <Route path='/Storeinfo' element={<Storeinfo/>} />
      <Route path='/Menu1' element={<Menu1/>} />
      <Route path='/Menu2' element={<Menu2/>} />
      <Route path='/Menu3' element={<Menu3/>} />
      <Route path='/Menu4' element={<Menu4/>} />
      <Route path='/Menu5' element={<Menu5/>} />
      <Route path="/qr" element={<QRScan />} />
    </Routes>
    </BrowserRouter>
  )
}

export default App
