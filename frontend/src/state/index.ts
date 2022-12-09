import {Models} from '@rematch/core'
import {lastUpdate} from './model/lastUpdate'

export interface RootModel extends Models<RootModel> {
    lastUpdate: typeof lastUpdate
}

export const models: RootModel = {lastUpdate}
