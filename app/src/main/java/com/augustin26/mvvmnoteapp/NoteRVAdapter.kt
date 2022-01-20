package com.augustin26.mvvmnoteapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class NoteRVAdapter(val context: Context, val noteClickDeleteInterface: NoteClickDeleteInterface, val noteClickInterface: NoteClickInterface) :
    RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    //모든 노트 목록에 대한 변수
    private val allNotes = ArrayList<Note>()

    //뷰 홀더 클래스
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //레이아웃의 모든 변수를 초기화
        val noteTV: TextView = itemView.findViewById<TextView>(R.id.idTVNote)
        val dateTV: TextView = itemView.findViewById<TextView>(R.id.idTVDate)
        val deleteIV: ImageView = itemView.findViewById<ImageView>(R.id.idIVDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //리사이클러뷰의 각 아이템들을 inflate 한다.
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_rv_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //리사이클러뷰 아이템의 데이터를 세팅한다.
        holder.noteTV.setText(allNotes.get(position).noteTitle)
        holder.dateTV.setText("Last Updated : "+allNotes.get(position).timeStamp)
        holder.deleteIV.setOnClickListener {
            // 삭제 다이얼로그
            val custom = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null)
            val builder = AlertDialog.Builder(context)
                .setView(custom)
                .setCancelable(false)
            val  dialog = builder.show()
            custom.findViewById<Button>(R.id.idBtnOK).setOnClickListener {
                //노트삭제클릭 인터페이스를 호출하고 포지션 전달
                noteClickDeleteInterface.onDeleteIconClick(allNotes[position])
                dialog.dismiss()
            }
            custom.findViewById<Button>(R.id.idBtnCancle).setOnClickListener {
                dialog.dismiss()
            }
        }

        //리사이클러뷰 아이템에 클릭 리스너를 추가
        holder.itemView.setOnClickListener {
            //노트클릭 인터페이스를 호출하고 포지션 전달
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
        //노트목록 갯수를 반환
        return allNotes.size
    }

    //노트목록을 업데이트하는 데 사용된다.
    fun updateList(newList: List<Note>) {
        //노트 목록 삭제
        allNotes.clear()
        //노트 목록에 새 목록을 추가
        allNotes.addAll(newList)
        //어댑터에 알리기 위해 데이터 변경 알림 메소드 호출
        notifyDataSetChanged()
    }

}
