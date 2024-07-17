export type Locales = 'en' | 'ko'

export type Pages = 'home' | 'header' | 'footer' | 'boards' | 'boards/post' | 'boards/update' | 'boards/board' | 'signin' | 'signup'

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
    post: string
    boards: string
    findMyLocal: string
    login: string
    logout: string
    mypage: string
    cancel: string
}

export interface LocaleFooter {
    home: string
    post: string
    boards: string
    login: string
    language: string
}

export interface LocalePostBoard {
    titlePlaceholder: string
    paragraph1: string
    paragraph2: string
    paragraph3: string
    paragraph4: string
    paragraph5: string
    findAddressPlaceholder: string
    templateButton: string
    post: string
    fileSizeError: string
    titleError: string
    imagesError: string
    addressError: string
    dateError: string
    timeError: string
    descriptionError: string
}

export interface LocaleUpdateBoard {
    titlePlaceholder: string
    paragraph1: string
    paragraph2: string
    paragraph3: string
    paragraph4: string
    paragraph5: string
    templateButton: string
    update: string
    fileSizeError: string
    titleError: string
    addressError: string
    dateError: string
    timeError: string
    descriptionError: string
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
    noBoards: string
}

export interface LocaleBoard {
    address: string
    availableTime: string
    description: string
    boardsButton: string
}

export interface LocaleSignin {
    goHome: string
    title: string
    signinGoogle: string
    email: string
    emailPlaceholder: string
    password: string
    passwordPlaceholder: string
    findPassword: string
    signin: string
    signup: string
    signupIntroduce: string

    emailError: string
    passwordError: string
    passwordLengthError: string
    passwordLetterError: string
    loginFailedError: string
}

export interface LocaleSignup {
    title: string
    signupGoogle: string
    signupEmail: string
    introduceTermsOfService: string
    termsOfService: string
    privacyPolicy: string
    introduceAlreadyHaveAccount: string
    signin: string

    beforeSignup: string
    nickname: string
    nicknamePlaceholder: string
    email: string
    emailPlaceholder: string
    password: string
    passwordPlaceholder: string
    phoneNumber: string
    phoneNumberPlaceholder: string
    agreeTerms: string
    signup: string

    nicknameError: string
    emailError: string
    passwordError: string
    passwordLengthError: string
    passwordLetterError: string
    phoneNumberError: string
    isAgreedError: string
}
