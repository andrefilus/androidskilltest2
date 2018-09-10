package andre_filus.com.br.cinq.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import andre_filus.com.br.cinq.R;
import andre_filus.com.br.cinq.adapters.AlbumAdapter;
import andre_filus.com.br.cinq.data.API.ApiData;
import andre_filus.com.br.cinq.data.callback.IDataReceiverCallback;
import andre_filus.com.br.cinq.databinding.FragmentAlbumListBinding;
import andre_filus.com.br.cinq.models.Album;
import andre_filus.com.br.cinq.models.User;
import andre_filus.com.br.cinq.utils.ErrorDialogUtils;

/**
 * Created by André Filus on 08/09/2018.
 */

public class AlbumListFragment extends Fragment implements AlbumAdapter.AlbumClickListener{

    private OnAlbumListFragmentActionListener mListener;
    private static final String EXTRA_USER = "x_user";
    private FragmentAlbumListBinding mFragmentBinding;
    private User mUser;

    public AlbumListFragment() {    }

    public static AlbumListFragment newInstance() {
        AlbumListFragment fragment = new AlbumListFragment();
        Bundle args = new Bundle();
//        args.putParcelable(EXTRA_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mUser = getArguments().getParcelable(EXTRA_USER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_album_list, container, false);
        return mFragmentBinding.getRoot();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAlbumListFragmentActionListener) {
            mListener = (OnAlbumListFragmentActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getAlbums();
    }

    private void getAlbums(){
        ApiData apiData = new ApiData();
        apiData.getAllAlbums(getContext(), getLoaderManager(), (ViewGroup) mFragmentBinding.recyclerAlbums, new IDataReceiverCallback<ArrayList<Album>>() {
            @Override
            public void onDataReceived(ArrayList<Album> data) {
                if(data != null && data.size() > 0){
                    mFragmentBinding.recyclerAlbums.setLayoutManager(new LinearLayoutManager(getContext()));
                    mFragmentBinding.recyclerAlbums.setAdapter(new AlbumAdapter(data,AlbumListFragment.this));
                    mFragmentBinding.txtNoResults.setVisibility(View.GONE);
                    return;
                }
                mFragmentBinding.recyclerAlbums.setVisibility(View.GONE);
                mFragmentBinding.txtNoResults.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDataNotReceived(@NonNull Throwable response) {
                ErrorDialogUtils.showDialogError(getContext(), mFragmentBinding.recyclerAlbums, getString(R.string.s_ok), response.getMessage());
            }
        });
    }

    public interface OnAlbumListFragmentActionListener {
    }
}
