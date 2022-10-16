package org.koreait.diary.commons;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.koreait.diary.R;

public class ConfirmMessage {

    public ConfirmMessage(Context context, String message, PositiveAction positiveAction, NegativeAction negativeAction) {
        this(context, message, positiveAction, negativeAction, null);
    }

    public ConfirmMessage(Context context, String message, PositiveAction positiveAction, NegativeAction negativeAction, NeutralAction neutralAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.confirm);
        builder.setMessage(message);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        // 예 버튼 추가
        if (positiveAction != null) {
            builder.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    positiveAction.apply(dialog, which);
                }
            });
        }

        // 아니오 버튼 추가
        if (negativeAction != null) {
            builder.setNegativeButton(R.string.confirm_no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    negativeAction.apply(dialog, which);
                }
            });
        }

        // 취소 버튼 추가
        if (neutralAction != null) {
            builder.setNeutralButton(R.string.confirm_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    neutralAction.apply(dialog, which);
                }
            });
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 예 버튼 클릭시 처리 인터페이스
    public static interface PositiveAction {
        void apply(DialogInterface dialog, int which);
    }

    // 아니오 버튼 클릭시 처리할 인터페이스
    public static interface NegativeAction {
        void apply(DialogInterface dialog, int which);
    }

    // 취소 버튼 클릭시 처리할 인터페이스
    public static interface NeutralAction {
        void apply(DialogInterface dialog, int which);
    }
}
