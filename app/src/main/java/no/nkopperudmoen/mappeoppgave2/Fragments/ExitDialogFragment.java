package no.nkopperudmoen.mappeoppgave2.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import no.nkopperudmoen.mappeoppgave2.R;

public class ExitDialogFragment extends DialogFragment {
    private DialogClickListener callback;

    public interface DialogClickListener {
        void onYesClick();

        void onNoClick();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callback = (DialogClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Kallende klasse må implementere interfacet");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setTitle(R.string.ExitDialogTitle)
                .setMessage(R.string.ExitDialogMsg)
                .setPositiveButton(getString(R.string.ja), (dialogInterface, i) -> callback.onYesClick())
                .setNegativeButton(getString(R.string.nei), (dialogInterface, i) -> callback.onNoClick()).create();
    }
}
