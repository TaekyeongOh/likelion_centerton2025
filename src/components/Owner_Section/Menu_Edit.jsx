import React from 'react'
import back from '../../assets/img/owner_menu_edit/back.svg'
import qr from '../../assets/img/owner_menu_edit/qr.svg'
import edit from '../../assets/img/owner_menu_edit/edit.svg'
import { Link } from 'react-router-dom'

const MenuEditSection = ({ section,deleteSection }) => (
  <div className="menu_section" key={section.id}>
    <div className="text">
     <h1>{section.name || '메뉴명을 적어주세요!'}</h1>
      <p>{section.description || '메뉴 설명을 해주세요. 자세하게 적을수록 손님들이 좋아해요.'}</p>
    </div>
    <div className="btn">
        <Link to={`/menu_edit_popup2/${section.id}`}>
        <div className="edit">
          <button className="edit_btn">편집</button>
        </div>
      </Link>
      <div className="delete">
          <button className="del_btn" onClick={()=> deleteSection(section.id)} >삭제</button>
        </div>
    </div>
    </div>
);

  const Menu_Edit = ({ storeInfo, selectedTags, menuSections, addMenuSection, deleteSection }) => {

  return (
    <div id="Menu_Edit_Wrap" className="container">
      <header>
        <div className="icon">
          <img src={back} className="back_icon" alt="" />
          <img src={qr} className="qr_icon" alt="" />
        </div>
        <div className="header"></div>
      </header>
      <main>
        <div className="store_info">
          <div className="title">MENU EDIT</div>
          <div className="store">
            <h1>{storeInfo.name || '한그릇'}</h1>
            <Link to='/menu_edit_popup1'>
                <div className="edit_icon">
                    <img src={edit} alt="" />
                </div>
            </Link>
          </div>
          <p>{storeInfo.description || '한국의 정을 담은 따뜻한 한 끼'}</p>
          <div className='text' >{storeInfo.detail || '"한그릇"은 계절마다 바뀌는 따끈한 국물 요리와 밥 한그릇을 정성스럽게 차려내는 따뜻한 동네 식당입니다.' }</div>
          <div className='map'>{storeInfo.address || '서울 서대문구 홍제5동 하나빌딩 1층'}</div>
          <div className="tags">
            {selectedTags && selectedTags.map(tag => (
              <div className="tag selected" key={tag}>{tag}</div>
            ))}
          </div>
        </div>
        <div className="title">메뉴 편집</div>
        <div className="menu_edit">
          {menuSections.map(section => (
            <MenuEditSection key={section.id} section={section} deleteSection={deleteSection}  />
          ))}
          <button className="menu_add" onClick={addMenuSection}>
            +
          </button>
          <button className="tanslation_btn">번역하기</button>
        </div>
      </main>
    </div>
  );
};


export default Menu_Edit