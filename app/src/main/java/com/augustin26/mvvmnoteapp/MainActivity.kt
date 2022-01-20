package com.augustin26.mvvmnoteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {

    // 뷰모델, 리사이클러뷰, FAB 선언
    lateinit var viewModal: NoteViewModal
    lateinit var notesRV: RecyclerView
    lateinit var addFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 변수 초기화
        notesRV = findViewById(R.id.notesRV)
        addFAB = findViewById(R.id.idFAB)

        // 리사이클러뷰의 레이아웃매니저 세팅
        notesRV.layoutManager = LinearLayoutManager(this)

        // 어댑터클래스 초기화
        val noteRVAdapter = NoteRVAdapter(this, this, this)

        // 리사이클러뷰의 어댑터 세팅
        notesRV.adapter = noteRVAdapter

        // 뷰모델 초기화
        viewModal = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModal::class.java)

        // 뷰모델의 라이브데이터의 변경 사항을 관찰
        viewModal.allNotes.observe(this, Observer { list ->
            list?.let {
                // 목록을 업데이트한다.
                noteRVAdapter.updateList(it)
            }
        })
        // fab 버튼리스너
        addFAB.setOnClickListener {
            // 새 노트를 작성하는 인텐트
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onNoteClick(note: Note) {
        // 새로운 인텐트를 열고 데이터를 전달
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
    }

    override fun onDeleteIconClick(note: Note) {
        // 노트 메모를 삭제하는 뷰모델 메소드
        viewModal.deleteNote(note)
        // 삭제 토스트 메시지
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }
}