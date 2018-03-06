package com.teaml.iq.volunteer.ui.group.edit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.ui.group.create.CreateGroupFragment
import com.teaml.iq.volunteer.utils.AppConstants
import kotlinx.android.synthetic.main.fragment_create_group.*
import org.jetbrains.anko.design.indefiniteSnackbar
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 05/03/2018.
 */
class EditGroupFragment : CreateGroupFragment(), EditGroupMvpView {


    companion object {
        val TAG: String = EditGroupFragment::class.java.simpleName
        fun newInstance(args: Bundle = Bundle.EMPTY) = EditGroupFragment().apply { arguments = args }
    }

    private var editMenuItem: MenuItem? = null

    @Inject
    lateinit var mEditPresenter: EditGroupMvpPresenter<EditGroupMvpView>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        getActivityComponent()?.let {
            it.inject(this)
            mEditPresenter.onAttach(this)
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setup(view: View) {
        groupLogoImgView.setOnClickListener { mEditPresenter.onLogoImgClick() }
        groupCoverImgView.setOnClickListener { mEditPresenter.onCoverImgClick() }
        mEditPresenter.loadGroupDetail()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mEditPresenter.onActivityResult(requestCode, resultCode, data)
    }


    override fun onSetToolbarTitle() = getString(R.string.edit_group)


    override fun showGroupDetail(groupInfo: FbGroup) {
        nameField.setText(groupInfo.name)
        bioField.setText(groupInfo.bio)

        // try old way of connecting string between each other
        val coverImgRef = FirebaseStorage.getInstance()
                .getReference(AppConstants.GROUP_COVER_IMG_FOLDER + "/" + groupInfo.coverImg)
        val logoImgRef = FirebaseStorage.getInstance()
                .getReference(AppConstants.GROUP_LOGO_IMG_FOLDER + "/" + groupInfo.logoImg)

        GlideApp.with(this)
                .load(coverImgRef)
                .signature(ObjectKey(groupInfo.lastModificationDate))
                .placeholder(R.drawable.group_cover_placeholder_img)
                .into(groupCoverImgView)

        GlideApp.with(this)
                .load(logoImgRef)
                .signature(ObjectKey(groupInfo.lastModificationDate))
                //.placeholder(R.drawable.group_logo_placeholder_img)
                .into(groupLogoImgView)

    }


    override fun showGroupDetailFragment(groupId: String) {
        Log.e(TAG, groupId)
        activity?.supportFragmentManager?.popBackStack()
        super.showGroupDetailFragment(groupId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        editMenuItem = menu.findItem(R.id.action_edit)
        editMenuItem!!.isVisible = false
    }

    override fun onLoadGroupInfoError() {
        indefiniteSnackbar(groupCoverImgView, R.string.connection_error, R.string.retry) {
            mEditPresenter.loadGroupDetail()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_create_group -> {
                val name = nameField.text.toString()
                val bio = bioField.text.toString()

                mEditPresenter.onActionDoneClick(name, bio)

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        editMenuItem?.isVisible = true
        mEditPresenter.onDetach()
        super.onDestroyView()
    }


}