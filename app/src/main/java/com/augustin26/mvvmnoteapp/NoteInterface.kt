package com.augustin26.mvvmnoteapp

interface NoteClickDeleteInterface {
    //삭제 이미지 클릭 메소드
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface {
    //리사이클러뷰 아이템 클릭 메소드
    fun onNoteClick(note: Note)
}