package org.lds.community.CallingWorkFlow.api;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import org.lds.community.CallingWorkFlow.R;

/**
 * Created with IntelliJ IDEA.
 * User: matts
 * Date: 8/21/12
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceException extends RuntimeException {

    public enum ServiceError {
        NONE,
         INVALID_CREDENTIALS,
         SERVICE_UNAVAILABLE,
         LOST_CONNECTION,
         NOT_A_MEMBER
    }

    private ServiceError errorCode = ServiceError.NONE;

    public ServiceException(ServiceError errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceException(ServiceError errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceError getErrorCode() {
        return errorCode;
    }

    public static void showError(Context context, ServiceError serviceError) {
        AlertDialog.Builder d = new AlertDialog.Builder(context);
        d.setTitle(R.string.error);

        switch (serviceError) {
            case SERVICE_UNAVAILABLE:
                d.setMessage(R.string.error_service_unavailable);
                break;
            case LOST_CONNECTION:
                d.setMessage(R.string.error_lost_connection);
                break;
            default:
                d.setMessage("Unknown Error");
                break;
        }

        d.setIcon(android.R.drawable.ic_dialog_alert);
        d.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        d.show();
    }

    @Override
    public String getMessage() {
        return errorCode.toString();
    }
}
