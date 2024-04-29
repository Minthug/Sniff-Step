import React from 'react'
import { LocaleFooter } from '@/app/types/locales'
import { Button, container } from '..'

interface Props {
    lang: string
    text: LocaleFooter
}

export function Desktop({ lang, text }: Props) {
    return (
        <footer className={container.footer.desktop}>
            <div className="flex justify-between items-center">
                <div>
                    <img className="w-[160px]" src="/images/text-logo-1.png" alt="" />
                </div>
                <div className="flex gap-16">
                    <Button contents={text.home} href={`/${lang}`} />
                    <Button contents={text.registerWalker} href={`/${lang}/register-walker`} />
                    <Button contents={text.boards} href={`/${lang}/boards`} />
                    <Button contents={text.login} href={`/${lang}/signin`} />
                    <Button contents={text.language} href={`/${lang === 'en' ? 'ko' : 'en'}`} />
                </div>
            </div>
            <div className="flex justify-between text-[12px] text-gray-400">
                <div className="flex gap-4">
                    <div>@ 2024 Sniff & Step</div>
                    <div>Terms</div>
                    <div>Privacy</div>
                </div>
                <div className="flex items-center gap-4">
                    <div>@Minthug</div>
                    <div>@VVSOGI</div>
                </div>
            </div>
        </footer>
    )
}
