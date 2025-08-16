import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Menu_Edit from './components/Owner_Section/Menu_Edit'
import Owner_home_second from './components/Owner_Section/Owner_home_second'
import Owner_home_first from './components/Owner_Section/Owner_home_first'
import Owner_home_third from './components/Owner_Section/Owner_home_third'
import Owner_home_fourth from './components/Owner_Section/Owner_home_fourth'
import Owner_home_fifth from './components/Owner_Section/Owner_home_fifth'

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/menu_edit' element={<Menu_Edit />} />
        <Route path='/owner_home_first' element={<Owner_home_first />} />
        <Route path='/owner_home_second' element={<Owner_home_second />} />
        <Route path='/owner_home_third' element={<Owner_home_third />} />
        <Route path='/owner_home_fourth' element={<Owner_home_fourth />} />
        <Route path='/owner_home_fifth' element={<Owner_home_fifth />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
