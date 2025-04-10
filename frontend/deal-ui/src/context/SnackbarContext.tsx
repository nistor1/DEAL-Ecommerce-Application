import React, { createContext, useContext } from 'react';
import { notification } from 'antd';

interface SnackbarContextProps {
    showError: (msg: string, description?: string) => void;
    showSuccess: (msg: string, description?: string) => void;
    showInfo: (msg: string, description?: string) => void;
    showWarning: (msg: string, description?: string) => void;
}

const SnackbarContext = createContext<SnackbarContextProps | undefined>(undefined);

export const SnackbarProvider: React.FC<React.PropsWithChildren<{}>> = ({ children }) => {
    // Use Ant Design's hook-based API to allow notifications to re-render on theme changes.
    const [api, contextHolder] = notification.useNotification();

    const openNotificationWithIcon = (
        type: 'success' | 'info' | 'warning' | 'error',
        message: string,
        description?: string
    ) => {
        api.open({
            type,
            message,
            description,
            placement: 'topRight',
            duration: 3,
        });
    };

    const showError = (msg: string, description?: string) => {
        openNotificationWithIcon('error', msg, description);
    };

    const showSuccess = (msg: string, description?: string) => {
        openNotificationWithIcon('success', msg, description);
    };

    const showInfo = (msg: string, description?: string) => {
        openNotificationWithIcon('info', msg, description);
    };

    const showWarning = (msg: string, description?: string) => {
        openNotificationWithIcon('warning', msg, description);
    };

    return (
        <SnackbarContext.Provider value={{ showError, showSuccess, showInfo, showWarning }}>
            {contextHolder}
            {children}
        </SnackbarContext.Provider>
    );
};

export const useSnackbar = (): SnackbarContextProps => {
    const context = useContext(SnackbarContext);
    if (!context) {
        throw new Error('useSnackbar must be used within a SnackbarProvider');
    }
    return context;
};
