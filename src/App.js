import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Menu from './components/Customer_Section/Menu'
import Menu_Best from './components/Customer_Section/Menu_Best'
import Menu_Language from './components/Customer_Section/Menu_Language'
import Menu_Table from './components/Customer_Section/Menu_Table'
import Cus_options from './components/Customer_Section/Cus_options'
import Cus_order from './components/Customer_Section/Cus_order'

const App = () => {
  return (
    <BrowserRouter>
    <Routes>
      <Route path='/' element={<Menu />} />
      <Route path='/menu_best' element={<Menu_Best />} />
      <Route path='/menu_language' element={<Menu_Language />} />
      <Route path='/menu_table' element={<Menu_Table />} />
      <Route path='/cus_order' element={<Cus_order />} />
      <Route path='/cus_options' element={<Cus_options />} />
    </Routes>
    </BrowserRouter>
  )
}

export default App
