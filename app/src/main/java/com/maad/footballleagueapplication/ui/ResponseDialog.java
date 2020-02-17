package com.maad.footballleagueapplication.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.maad.footballleagueapplication.R;

class ResponseDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setMessage(R.string.not_allowed_dialog_message)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss());
        return builder.create();
    }
}
