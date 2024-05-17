import React from 'react'
import Link from 'next/link'
import { D2CodingBold } from '@/app/fonts'
import { GoogleButton, SeparateLine, LargeButton, Input, TextLogo, container } from '@/app/common'
import { useRouter } from 'next/navigation'
import { LoginStates } from '@/app/hooks'
import { LocaleSignin } from '@/app/types/locales'

interface Props {
    lang: string
    text: LocaleSignin
    loginStates: LoginStates
}

export function Mobile({ lang, text, loginStates }: Props) {
    const router = useRouter()
    const {
        email,
        password,
        emailError,
        passwordError,
        passwordLengthError,
        passwordLetterError,
        loginFailedError,
        changeEmail,
        changePassword,
        handleLogin,
        handleGetProfile,
        handleGoogleLogin
    } = loginStates

    return (
        <div className={container.autentication.mobile.section}>
            <div className={container.autentication.mobile.main}>
                <TextLogo lang={lang} />
                <GoogleButton onClick={handleGoogleLogin} className="mb-4 active:bg-slate-100">
                    {text.signinGoogle}
                </GoogleButton>
                <SeparateLine className="mb-0 text-[12px]">or</SeparateLine>
                <div className="my-4">
                    <div className={`${D2CodingBold.className} mb-1 text-[14px]`}>{text.email}</div>
                    <Input type="text" value={email} onChange={changeEmail} placeholder={text.emailPlaceholder} />
                </div>
                {emailError && <div className="text-red-500 text-[12px] mb-4">{text.emailError}</div>}
                <div className="mb-8">
                    <div className="flex justify-between">
                        <div className={`${D2CodingBold.className} mb-1 text-[14px]`}>{text.password}</div>
                    </div>
                    <Input type="password" value={password} onChange={changePassword} placeholder={text.passwordPlaceholder} />
                    {passwordError && <div className="text-red-500 text-[12px] my-4">{text.passwordError}</div>}
                    {passwordLengthError && <div className="text-red-500 text-[12px] mb-4">{text.passwordLengthError}</div>}
                    {passwordLetterError && <div className="text-red-500 text-[12px]">{text.passwordLetterError}</div>}
                    {loginFailedError && <div className="text-red-500 text-[12px] my-4">{text.loginFailedError}</div>}
                </div>
                <LargeButton
                    className="active:bg-gray-800"
                    theme="dark"
                    onClick={async () => {
                        try {
                            const accessToken = await handleLogin()
                            await handleGetProfile(accessToken)
                            router.push(`/${lang}`)
                        } catch (err) {
                            console.log(err)
                        }
                    }}
                >
                    {text.signin}
                </LargeButton>
                <div className="flex gap-2 text-[12px] justify-center">
                    <div>{text.signupIntroduce}</div>
                    <Link className="underline select-none active:bg-slate-100" href={`/${lang}/signup`}>
                        {text.signup}
                    </Link>
                </div>
            </div>
        </div>
    )
}
