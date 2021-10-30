package no.nkopperudmoen.mappeoppgave2.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setTitle("Advarsel!")
                .setMessage("Er du sikker på at du vil avbryte? Alle data vil bli resatt")
                .setPositiveButton("Ja", (dialogInterface, i) -> callback.onYesClick())
                .setNegativeButton("Nei", (dialogInterface, i) -> callback.onNoClick()).create();
    }
}
