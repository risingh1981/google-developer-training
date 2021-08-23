package com.example.android.devbyteviewer.databinding;
import com.example.android.devbyteviewer.R;
import com.example.android.devbyteviewer.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentDevByteBindingImpl extends FragmentDevByteBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.recycler_view, 2);
    }
    // views
    @NonNull
    private final android.widget.FrameLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentDevByteBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private FragmentDevByteBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ProgressBar) bindings[1]
            , (androidx.recyclerview.widget.RecyclerView) bindings[2]
            );
        this.loadingSpinner.setTag(null);
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.viewModel == variableId) {
            setViewModel((com.example.android.devbyteviewer.viewmodels.DevByteViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.example.android.devbyteviewer.viewmodels.DevByteViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelPlaylist((androidx.lifecycle.LiveData<java.util.List<com.example.android.devbyteviewer.domain.Video>>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelPlaylist(androidx.lifecycle.LiveData<java.util.List<com.example.android.devbyteviewer.domain.Video>> ViewModelPlaylist, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.example.android.devbyteviewer.viewmodels.DevByteViewModel viewModel = mViewModel;
        androidx.lifecycle.LiveData<java.util.List<com.example.android.devbyteviewer.domain.Video>> viewModelPlaylist = null;
        java.util.List<com.example.android.devbyteviewer.domain.Video> viewModelPlaylistGetValue = null;

        if ((dirtyFlags & 0x7L) != 0) {



                if (viewModel != null) {
                    // read viewModel.playlist
                    viewModelPlaylist = viewModel.getPlaylist();
                }
                updateLiveDataRegistration(0, viewModelPlaylist);


                if (viewModelPlaylist != null) {
                    // read viewModel.playlist.getValue()
                    viewModelPlaylistGetValue = viewModelPlaylist.getValue();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            com.example.android.devbyteviewer.util.BindingAdaptersKt.goneIfNotNull(this.loadingSpinner, viewModelPlaylistGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.playlist
        flag 1 (0x2L): viewModel
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}