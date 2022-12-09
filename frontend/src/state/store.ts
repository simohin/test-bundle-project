import {init, RematchDispatch, RematchRootState} from '@rematch/core'
import {models, RootModel} from "./index";

export const store = init({
    models,
})

export type Dispatch = RematchDispatch<RootModel>
export type RootState = RematchRootState<RootModel>
