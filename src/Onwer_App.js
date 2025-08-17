import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Menu_Edit from './components/Owner_Section/Menu_Edit'
import Menu_English from './components/Owner_Section/Menu_English'
import Menu_Japanese from './components/Owner_Section/Menu_Japanese'
import Menu_Chinese from './components/Owner_Section/Menu_Chinese'
import Owner_QR from './components/Owner_Section/Owner_QR'

const App = () => {
  return (
    <BrowserRouter>
    <Routes>
      <Route path='/menu_edit' element={<Menu_Edit />} />
      <Route path='/menu_english' element={<Menu_English />} />
      <Route path='/menu_japanese' element={<Menu_Japanese />} />
      <Route path='/menu_chinese' element={<Menu_Chinese />} />
      <Route path='/owner_qr' element={<Owner_QR />} />
    </Routes>
    </BrowserRouter>
  )
}

export default App
