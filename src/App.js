import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Main from './components/Customer_Section/Main'
import Cus_options from './components/Customer_Section/Cus_options'
import Cus_order from './components/Customer_Section/Cus_order'

const App = () => {
  return (
    <BrowserRouter>
    <Routes>
      <Route path='/' element={<Main />} />
      <Route path='/cus_order' element={<Cus_order />} />
      <Route path='/cus_options' element={<Cus_options />} />
    </Routes>
    </BrowserRouter>
  )
}

export default App
