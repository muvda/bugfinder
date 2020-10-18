package ca.sfu.cmpt276.as3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MessageFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.win_message,null);

        DialogInterface.OnClickListener listener = (dialog, which) -> getActivity().finish();

        return new AlertDialog.Builder(getActivity())
                .setTitle("Game over!").setView(view)
                .setPositiveButton(android.R.string.ok,listener).create();
    }
}
