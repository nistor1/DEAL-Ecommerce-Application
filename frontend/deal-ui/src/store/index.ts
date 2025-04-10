//TODO Add here the reducers needed under the configureStore/reducer
//TODO The slice definitions should be under the slices directory

import {configureStore} from "@reduxjs/toolkit";

const store = configureStore({
    reducer: {
    }
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;