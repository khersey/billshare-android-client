package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.graphics.Canvas
import android.support.design.widget.Snackbar
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.entity.Pet

class PetsListAdapter(private val mPets: MutableList<Pet>,
                      private val mOnPetItemClickListener: PetsListAdapter.OnPetItemClickListener,
                      private val mOnItemDeletedListener: PetsListAdapter.OnItemDeletedListener) :
        RecyclerView.Adapter<PetsListAdapter.PetViewHolder>() {

    interface OnPetItemClickListener {
        fun onPetItemClick(pet: Pet)

        fun onEditPetItemClick(pet: Pet)
    }

    interface OnItemDeletedListener {
        fun onPetItemDeleted(pet: Pet)
    }

    private val TAG = PetsListAdapter::class.java.canonicalName
    lateinit private var mRecyclerView: RecyclerView

    var pets: List<Pet>
        get() = mPets
        set(pets) {
            this.mPets.clear()
            this.mPets.addAll(pets)
            notifyDataSetChanged()
        }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(AdapterItemTouchHelperCallback(0, ItemTouchHelper.RIGHT))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        mRecyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_pet_list_item, parent, false)

        return PetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val petName = mPets[position].name
        val petStatus = mPets[position].status

        holder.bindData(petName!!, petStatus!!)
    }

    override fun getItemCount(): Int {
        return mPets.size
    }

    fun onItemRemoved(viewHolder: RecyclerView.ViewHolder) {
        val itemPosition = viewHolder.adapterPosition
        val removedPet = mPets[itemPosition]

        Snackbar.make(mRecyclerView, R.string.delete_pet_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo_action, { onItemRemovedUndoAction(itemPosition, removedPet) })
                .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(snackBar: Snackbar?, event: Int) {
                        if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                            mOnItemDeletedListener.onPetItemDeleted(removedPet)
                        }
                    }
                }).show()

        mPets.removeAt(itemPosition)
        notifyItemRemoved(itemPosition)
    }

    private fun onItemRemovedUndoAction(previousItemPosition: Int, removedPet: Pet) {
        mPets.add(previousItemPosition, removedPet)
        notifyItemInserted(previousItemPosition)
        mRecyclerView.scrollToPosition(previousItemPosition)
    }

    inner class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.card_view)
        lateinit var mCardView: CardView
        @BindView(R.id.pet_name)
        lateinit var mNameTV: TextView
        @BindView(R.id.pet_status)
        lateinit var mStatusTV: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(petName: String, petStatus: String) {
            mNameTV.text = petName
            mStatusTV.text = petStatus
        }

        @OnClick(R.id.edit_button)
        fun onEditClicked() {
            mOnPetItemClickListener.onEditPetItemClick(mPets[adapterPosition])
        }

        @OnClick(R.id.card_view)
        fun onCardClicked() {
            mOnPetItemClickListener.onPetItemClick(mPets[adapterPosition])
        }
    }

    private inner class AdapterItemTouchHelperCallback(dragDirs: Int, swipeDirs: Int) :
            ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onItemRemoved(viewHolder)
        }

        override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
            ItemTouchHelper.Callback.getDefaultUIUtil().clearView((viewHolder as PetViewHolder).mCardView)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (viewHolder != null) {
                ItemTouchHelper.Callback.getDefaultUIUtil().onSelected((viewHolder as PetViewHolder).mCardView)
            }
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                 dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView,
                    (viewHolder as PetViewHolder).mCardView, dX, dY, actionState, isCurrentlyActive)
        }

        override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView,
                    (viewHolder as PetViewHolder).mCardView, dX, dY, actionState, isCurrentlyActive)
        }
    }
}