import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Menu_Edit from './components/Owner_Section/Menu_Edit'
import Owner_home_second from './components/Owner_Section/Owner_home_second'
import Owner_home_first from './components/Owner_Section/Owner_home_first'

const App = () => {
  return (
    <BrowserRouter>
    <Routes>
      <Route path='/menu_edit' element={<Menu_Edit />} />
            <Route path='/owner_home_first' element={<Owner_home_first />} />
      <Route path='/owner_home_second' element={<Owner_home_second />} />
    </Routes>
    </BrowserRouter>
  )
}

export default App
