import { Locales, Pages } from '@/app/types/locales'

export async function getLocales<T>(page: Pages, locale: Locales): Promise<T> {
    const data = require(`/public/locales/${page}/${locale}.json`)

    return data
}
