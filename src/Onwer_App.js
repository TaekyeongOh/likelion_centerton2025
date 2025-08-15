import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Menu_Edit from './components/Owner_Section/Menu_Edit'

const App = () => {
  return (
    <BrowserRouter>
    <Routes>
      <Route path='/menu_edit' element={<Menu_Edit />} />
    </Routes>
    </BrowserRouter>
  )
}

export default App
