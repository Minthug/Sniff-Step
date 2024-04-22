export type Locales = 'en' | 'ko'

export type Pages = 'home' | 'header' | 'footer'

export interface LocaleHome {
    section1: {
        catchPhrase1: string
        catchPhrase2: string
    }

    section2: {
        title: string
        catchPhrase1: string
        catchPhrase2: string
        description: string
    }

    section3: {
        title: string
        catchPhrase1: string
        catchPhrase2: string
        box1: string
        box2: string
        box3: string
    }

    section4: {
        title: string
    }

    Link: string
}

export interface LocaleHeader {
    registerWalker: string
    boards: string
    findMyLocal: string
    login: string
    cancel: string
}

export interface LocaleFooter {
    home: string
    registerWalker: string
    boards: string
    login: string
}
