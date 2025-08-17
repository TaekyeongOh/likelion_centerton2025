import React, { useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Menu_Edit from './components/Owner_Section/Menu_Edit';
import Menu_Edit_Popup1 from './components/Owner_Section/Menu_Edit_Popup1';
import Menu_Edit_Popup2 from './components/Owner_Section/Menu_Edit_Popup2';

const App = () => {

  //메뉴명, 메뉴 설명, 가격, 이미지
  const [menuSections, setMenuSections] = useState([
    { id: 0, name: '', description: '', price: '', imagePreviewUrl:null },
  ]);

  // 메뉴 섹션 추가
  const addMenuSection = () => {
    setMenuSections(prev => [
      ...prev,
      { id: prev.length, name: '', description: '', price: '' },
    ]);
  };

  // 메뉴 -  편집
  const updateMenuSection = (id, newdata) => {
    setMenuSections(prev =>
      prev.map(section =>
        section.id === id ? { ...section, ...newdata } : section
      )
    );
  };

   // 메뉴 - 삭제
const deleteMenuSection = (id) => {
  setMenuSections(prev => prev.filter(section => section.id !== id));
};

  // 가게 정보
  const [storeInfo, setStoreInfo] = useState({
    name: '',
    description: '',
    detail: '',
    address: '',
  });

  const saveStoreData = (info, tags) => {
    setStoreInfo(info);
    setSelectedTags(tags);
  };


  //태그
  const [selectedTags, setSelectedTags] = useState([]);

  const saveTags = (tags) => {
    setSelectedTags(tags);
  };

  //이미지 업로드
  const [imageFile, setImageFile] = useState(null);
  const [imagePreviewUrl, setImagePreviewUrl] = useState(null);

  const saveImage = (file, previewUrl) => {
    setImageFile(file);
    setImagePreviewUrl(previewUrl);
  };

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/menu_edit"
          element={
            <Menu_Edit
              storeInfo={storeInfo}
              selectedTags={selectedTags}
              saveTags={saveTags}
              menuSections={menuSections}
              addMenuSection={addMenuSection}
              deleteSection={deleteMenuSection}
            />
          }
        />
        <Route
          path="/menu_edit_popup1"
          element={
            <Menu_Edit_Popup1
              storeInfo={storeInfo}
              selectedTags={selectedTags}
              saveStoreData={saveStoreData}
            />
          }
        />
        <Route
          path="/menu_edit_popup2/:id"
          element={
            <Menu_Edit_Popup2
              menuSections={menuSections}
              updateMenuSection={updateMenuSection}
              imagePreviewUrl={imagePreviewUrl}
              saveImage={saveImage}
            />
          }
        />
        </Routes>
    </BrowserRouter>
  );
};

export default App;
