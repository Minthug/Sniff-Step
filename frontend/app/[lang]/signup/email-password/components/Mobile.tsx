import React from 'react'
import Link from 'next/link'
import { D2CodingBold } from '@/app/fonts'
import { LargeButton, Input, TextLogo, container } from '@/app/common'
import { LocaleSignup, Locales } from '@/app/types/locales'
import { SignupStates } from '@/app/hooks/useSignup'
import { useRouter } from 'next/navigation'

interface Props {
    lang: Locales
    text: LocaleSignup
    signupStates: SignupStates
}
export function Mobile({ lang, text, signupStates }: Props) {
    const router = useRouter()
    const {
        nickname,
        email,
        password,
        phoneNumber,
        nicknameError,
        emailError,
        passwordError,
        passwordLengthError,
        passwordLetterError,
        phoneNumberError,
        isAgreedError,
        changeNickname,
        changeEmail,
        changePassword,
        changePhoneNumber,
        changeIsAgreed,
        handleLogin,
        handleRegister
    } = signupStates

    return (
        <div className={container.autentication.mobile.section}>
            <div className={container.autentication.mobile.main}>
                <TextLogo lang={lang} />
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.nickname}</div>
                    <Input type="text" value={nickname} onChange={changeNickname} placeholder={text.nicknamePlaceholder} />
                </div>
                {nicknameError && <div className="text-red-500 text-[12px] mb-4">{text.nicknameError}</div>}
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.email}</div>
                    <Input type="text" value={email} onChange={changeEmail} placeholder={text.emailPlaceholder} />
                </div>
                {emailError && <div className="text-red-500 text-[12px] mb-4">{text.emailError}</div>}
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.password}</div>
                    <Input type="password" value={password} onChange={changePassword} placeholder={text.passwordPlaceholder} />
                </div>
                {passwordError && <div className="text-red-500 text-[12px] mb-4">{text.passwordError}</div>}
                {passwordLengthError && <div className="text-red-500 text-[12px] mb-4">{text.passwordLengthError}</div>}
                {passwordLetterError && <div className="text-red-500 text-[12px] mb-4">{text.passwordLetterError}</div>}
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.phoneNumber}</div>
                    <Input type="text" value={phoneNumber} onChange={changePhoneNumber} placeholder={text.phoneNumberPlaceholder} />
                </div>
                {phoneNumberError && <div className="text-red-500 text-[12px] mb-4">{text.phoneNumberError}</div>}
                <div className="flex items-center gap-4 mb-8">
                    <input type="checkbox" onChange={changeIsAgreed} className="w-[20px] h-[20px]" />
                    <div className="flex flex-wrap text-[12px]">
                        <div>{text.agreeTerms}&nbsp;</div>
                        <button className="underline select-none">{text.termsOfService}</button>,&nbsp;
                        <button className="underline select-none">{text.privacyPolicy}</button>
                    </div>
                </div>
                {isAgreedError && <div className="text-red-500 text-[12px] mb-4">{text.isAgreedError}</div>}
                <LargeButton
                    theme="dark"
                    onClick={async () => {
                        const isRegister = await handleRegister()
                        if (isRegister) {
                            await handleLogin()
                            router.push(`/${lang}/`)
                        }
                    }}
                    className="active:bg-gray-800"
                >
                    {text.signup}
                </LargeButton>
                <div className="flex justify-center items-center text-[12px]">
                    {text.introduceAlreadyHaveAccount}&nbsp;
                    <Link className="underline select-none active:bg-slate-100" href={`/${lang}/signin`}>
                        {text.signin}
                    </Link>
                </div>
            </div>
        </div>
    )
}
