import React from 'react'
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
        name,
        nickName,
        email,
        password,
        phoneNumber,
        changeName,
        changeNickname,
        changeEmail,
        changePassword,
        changePhoneNumber,
        changeIsAgreed
    } = signupStates
    return (
        <div className={container.autentication.mobile.section}>
            <div className={container.autentication.mobile.main}>
                <TextLogo lang={lang} />
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.nickname}</div>
                    <Input value={name} placeholder={text.nicknamePlaceholder} type="text" onChange={changeName} />
                </div>
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.email}</div>
                    <Input value={email} placeholder={text.emailPlaceholder} type="text" onChange={changeEmail} />
                </div>
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.password}</div>
                    <Input value={password} placeholder={text.passwordPlaceholder} type="password" onChange={changePassword} />
                </div>
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.phoneNumber}</div>
                    <Input value={phoneNumber} placeholder={text.phoneNumberPlaceholder} type="text" onChange={changePhoneNumber} />
                </div>
                <div className="flex items-center gap-4 mb-8">
                    <input onChange={changeIsAgreed} className="w-[20px] h-[20px]" type="checkbox" />
                    <div className="flex flex-wrap text-[12px]">
                        <div>{text.agreeTerms}&nbsp;</div>
                        <button className="underline select-none">{text.termsOfService}</button>,&nbsp;
                        <button className="underline select-none">{text.privacyPolicy}</button>
                    </div>
                </div>
                <LargeButton theme="dark" onClick={() => {}} className="active:bg-gray-800">
                    {text.signup}
                </LargeButton>
                <div className="flex justify-center items-center text-[12px]">
                    {text.introduceAlreadyHaveAccount}&nbsp;
                    <button className="underline select-none active:bg-slate-100" onClick={() => router.push(`/${lang}/signin`)}>
                        {text.signin}
                    </button>
                </div>
            </div>
        </div>
    )
}
