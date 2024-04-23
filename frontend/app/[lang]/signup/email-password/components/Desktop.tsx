import React from 'react'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import { LocaleSignup, Locales } from '@/app/types/locales'
import { SignupStates } from '@/app/hooks/useSignup'
import { D2CodingBold } from '@/app/fonts'
import { FaLongArrowAltLeft } from 'react-icons/fa'
import { SigninButton, SigninInput, container } from '@/app/common'

interface Props {
    lang: Locales
    text: LocaleSignup
    signupStates: SignupStates
}

export function Desktop({ lang, text, signupStates }: Props) {
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
        <div className={container.autentication.desktop.section}>
            <div className={container.autentication.desktop.sidebar}>
                <Link
                    className="absolute left-8 h-[60px] flex gap-2 justify-center items-center animate-leftBounce z-10"
                    href={`/${lang}/signup`}
                >
                    <FaLongArrowAltLeft className="w-[20px] h-[20px]" />
                    <div className={`${D2CodingBold.className} text-[14px]`}>{text.beforeSignup}</div>
                </Link>
                <video
                    className="w-full h-full object-contain select-none bg-[#a6bee4]"
                    autoPlay
                    muted
                    loop
                    src="https://cdn.dribbble.com/users/1341046/screenshots/16057590/media/db8ececb62cdd03ddaa78fab991a4ec3.mp4"
                />
                <img className="absolute bottom-4 w-[140px] object-cover" src="/images/text-logo.png" alt="" />
            </div>
            <div className={container.autentication.desktop.main}>
                <div className={`${D2CodingBold.className} text-[28px] mb-4`}>{text.title}</div>
                <div className="flex gap-4 mb-4">
                    <div className="w-full">
                        <div className={`${D2CodingBold.className} mb-2`}>{text.nickname}</div>
                        <SigninInput value={nickName} placeholder={text.nicknamePlaceholder} type="text" onChange={changeNickname} />
                    </div>
                </div>
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.email}</div>
                    <SigninInput value={email} placeholder={text.emailPlaceholder} type="text" onChange={changeEmail} />
                </div>
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.password}</div>
                    <SigninInput value={password} placeholder={text.passwordPlaceholder} type="password" onChange={changePassword} />
                </div>
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.phoneNumber}</div>
                    <SigninInput value={phoneNumber} placeholder={text.phoneNumberPlaceholder} type="text" onChange={changePhoneNumber} />
                </div>
                <div className="flex items-center gap-4 mb-8">
                    <input onChange={changeIsAgreed} className="w-[20px] h-[20px]" type="checkbox" />
                    <div className="flex flex-wrap text-[12px]">
                        <div>{text.agreeTerms}&nbsp;</div>
                        <button className="underline select-none">{text.termsOfService}</button>,&nbsp;
                        <button className="underline select-none">{text.privacyPolicy}</button>
                    </div>
                </div>
                <SigninButton theme="dark" onClick={() => {}}>
                    {text.signup}
                </SigninButton>
                <div className="flex justify-center items-center text-[12px]">
                    {text.introduceAlreadyHaveAccount}&nbsp;
                    <button className="underline select-none" onClick={() => router.push(`/${lang}/signin`)}>
                        {text.signin}
                    </button>
                </div>
            </div>
        </div>
    )
}
