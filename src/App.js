import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Menu from './components/Customer_Section/Menu'
import Menu_Best from './components/Customer_Section/Menu_Best'
import Menu_Language from './components/Customer_Section/Menu_Language'

const App = () => {
  return (
    <BrowserRouter>
    <Routes>
      <Route path='/' element={<Menu />} />
      <Route path='/menu_best' element={<Menu_Best />} />
      <Route path='/menu_language' element={<Menu_Language />} />
    </Routes>
    </BrowserRouter>
  )
}

export default App
