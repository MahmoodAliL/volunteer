package com.teaml.iq.volunteer.ui.group.create

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.ui.group.detail.GroupDetailFragment
import com.teaml.iq.volunteer.utils.replaceFragment
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_create_group.*
import org.jetbrains.anko.alert
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 03/03/2018.
 */
open class CreateGroupFragment : BaseFragment(), CreateGroupMvpView {


    companion object {
        val TAG: String = CreateGroupFragment::class.java.simpleName

        fun newInstance(args: Bundle = Bundle.EMPTY) = CreateGroupFragment().apply {
            arguments = args
        }
    }

    @Inject
    lateinit var mPresenter: CreateGroupMvpPresenter<CreateGroupMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = onSetToolbarTitle()

    }


    protected open fun onSetToolbarTitle() = getString(R.string.create_group)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        return inflater.inflate(R.layout.fragment_create_group, container, false)
    }

    override fun setup(view: View) {
        groupLogoImgView.setOnClickListener { mPresenter.onLogoImgClick() }
        groupCoverImgView.setOnClickListener { mPresenter.onCoverImgClick() }
    }

    override fun openCropLogoImg() {
        context?.let {
            CropImage.activity()
                    .setAspectRatio(1, 1)
                    .setRequestedSize(300, 300)
                    .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setOutputCompressQuality(70)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .start(it, this)
        }
    }

    override fun openCropCoverImg() {
        context?.let {
            CropImage.activity()
                    .setAspectRatio(16, 9)
                    //.setRequestedSize(1280, 720)
                    .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setOutputCompressQuality(60)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .start(it, this)
        }
    }

    override fun updateCoverImg(uri: Uri) {
        GlideApp.with(this)
                .load(uri)
                .placeholder(R.drawable.campaign_placeholder_img)
                .into(groupCoverImgView)
    }

    override fun updateLogoImg(uri: Uri) {
        GlideApp.with(this)
                .load(uri)
                .circleCrop()
                .placeholder(R.drawable.group_logo_placeholder_img)
                .into(groupLogoImgView)
    }

    override fun showReadExternalStorageRationale() {
        activity?.alert(R.string.read_external_storage_rationale_for_select_image, null, {
            positiveButton(R.string.ok) {
                mPresenter.onRequestReadExternalStoragePermission()
            }
            negativeButton(R.string.cancel) { }
        })?.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.create_group_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_create_group -> {
                val name = nameField.text.toString()
                val bio = bioField.text.toString()

                mPresenter.onActionDoneClick(name, bio)

            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun showGroupDetailFragment(groupId: String) {

        val bundle = Bundle()
        bundle.putString(GroupDetailFragment.BUNDLE_KEY_GROUP_ID, groupId)
        activity?.replaceFragment(
                R.id.fragmentContainer,
                GroupDetailFragment.newInstance(bundle),
                GroupDetailFragment.TAG
        )
    }

    override fun onDestroyView() {
        Log.e(TAG, "view Destroyed")
        mPresenter.onDetach()
        super.onDestroyView()
    }

}// Required empty public constructor
