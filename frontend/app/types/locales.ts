export type Locales = 'en' | 'ko'

export type Pages = 'home' | 'header' | 'footer' | 'register-walker' | 'boards'

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

export interface LocaleRegisterWalker {
    titlePlaceholder: string
    paragraph1: string
    paragraph2: string
    paragraph3: string
    paragraph4: string
    paragraph5: string
    templateButton: string
    register: string
    fileSizeError: string
}

export interface LocaleBoards {
    banner: {
        desktop: {
            catchPhrase1: string
            catchPhrase2: string
            catchPhrase3: string
            catchPhrase4: string
        }
        mobile: {
            catchPhrase1: string
            catchPhrase2: string
            catchPhrase3: string
            catchPhrase4: string
        }
    }

    title: string
}
