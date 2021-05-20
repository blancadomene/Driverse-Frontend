package com.blancadomene.tfg;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout linearLayout = null;
    private View view = null;
    private Button button = null;
    //TODO : ArrayList<String> searchesExample = new ArrayList<>(Arrays.asList("me", "quiero", "morir", "dsakjhdakhgdjahgsdjagsdjgasjdgajshdgcsfadasdadadga", "dsa","dasd","dsad","dasd","das","sdada","dasda","dsadad","dsada","dsada","dsada","daskjhd","11111"));
    ArrayList<Ride> searches = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        linearLayout = view.findViewById(R.id.search_result_container);     // Inflate the layout for this fragment

        button = (Button) view.findViewById(R.id.search_travel_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResultList(v);
            }
        });
        return view;
    }

    public void showResultList(View view) {
        searches.clear(); //TODO: delete examples
        Calendar date = new GregorianCalendar(2021,1,2, 13, 24, 53);
        Ride ride = new Ride("1", "Almería", date, "Granada", date, 3, new BigDecimal(123));
        searches.add(ride);
        linearLayout.removeAllViews();
        for (int i = 0; i < searches.size(); i++) {
            linearLayout.addView(searches.get(i).getRideCardView(getActivity()));
        }
    }

/* 
    public void showResultList(View view) {
        linearLayout.removeAllViews();
        CardView card = null;
        TextView textView = null;
        for (int i = 0; i < searches.size(); i++) {
            card = createBlankSearchCard();
            textView = new TextView(getActivity());
            textView.setText(searchesExample.get(i));
            card.addView(textView);
            linearLayout.addView(card);
        }
    }

    private CardView createBlankSearchCard() {
        CardView card = new CardView(getActivity());

        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int margin = dpToPx(8);
        layoutparams.setMargins(margin, margin, margin, margin);

        card.setLayoutParams(layoutparams);
        card.setRadius(15);
        card.setPadding(25, 25, 25, 25);
        card.setCardElevation(20);

        return card;
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }*/
}


