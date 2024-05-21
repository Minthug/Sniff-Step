'use client'

import React, { useState } from 'react'
import { container } from '@/app/common'
import { LocalePostBoard, Locales } from '@/app/types/locales'
import { FileChange, BoardState } from '@/app/hooks'
import { ChooseImageFile, ChooseWalkDates, ChooseWalkTimes, DescriptionModal, DescriptionTextarea, FindAddress } from '.'

interface Props {
    lang: Locales
    text: LocalePostBoard
    fileChangeState: FileChange
    boardState: BoardState
}

export function Mobile({ lang, text, fileChangeState, boardState }: Props) {
    const [isSubmitting, setIsSubmitting] = useState(false)
    const { file, fileSizeError, handleFileChange } = fileChangeState
    const {
        days,
        times,
        title,
        description,
        address,
        addressEnglish,
        descriptionExample,
        showDescriptionModal,
        titleError,
        addressError,
        dateError,
        timeError,
        descriptionError,
        descriptionSizeError,
        handleDayChange,
        handleTimeChange,
        handleTitleChange,
        handleChangeAddress,
        changeDayToKorean,
        changeTimeToKorean,
        handleDescriptionChange,
        setShowDescriptionModal,
        handlePost
    } = boardState

    return (
        <div className={container.main.mobile}>
            <div className="mb-4 border-b">
                <input
                    type="text"
                    value={title}
                    onChange={handleTitleChange}
                    className="w-full bg-[transparent] outline-none text-[20px] placeholder:text-[#d9d9d9]"
                    placeholder={text.titlePlaceholder}
                />
            </div>
            <div>
                <div className="mb-8">
                    <div className="mb-4 text-[16px] font-[500]">1. {text.paragraph1}</div>
                    <ChooseImageFile file={file} handleFileChange={handleFileChange} />
                    {fileSizeError && <div className="text-[12px] text-[#ff0000]">{text.fileSizeError}</div>}
                </div>
                <div className="mb-8">
                    <div className="mb-4 text-[16px] font-[500]">2. {text.paragraph2}</div>
                    <FindAddress
                        lang={lang}
                        text={text}
                        address={address}
                        addressEnglish={addressEnglish}
                        handleChangeAddress={handleChangeAddress}
                    />
                </div>
                <div className="mb-8">
                    <div className="mb-4 text-[16px] font-[500]">3. {text.paragraph3}</div>
                    <ChooseWalkDates days={days} lang={lang} handleDayChange={handleDayChange} changeDayToKorean={changeDayToKorean} />
                </div>
                <div className="mb-8">
                    <div className="mb-4 text-[16px] font-[500]">4. {text.paragraph4}</div>
                    <ChooseWalkTimes
                        times={times}
                        lang={lang}
                        handleTimeChange={handleTimeChange}
                        changeTimeToKorean={changeTimeToKorean}
                    />
                </div>
                <div className="mb-8">
                    <div className={`flex flex-col justify-between mb-4 text-[16px] font-[500] gap-2`}>
                        <div className="mb-2">5. {text.paragraph5}</div>
                        <button
                            onClick={() => setShowDescriptionModal(!showDescriptionModal)}
                            className="px-4 py-4 border rounded-sm bg-white text-[14px]"
                        >
                            {text.templateButton}
                        </button>
                    </div>
                    <DescriptionTextarea
                        description={description}
                        descriptionExample={descriptionExample}
                        descriptionSizeError={descriptionSizeError}
                        handleDescriptionChange={handleDescriptionChange}
                    />
                </div>
            </div>
            {showDescriptionModal && (
                <DescriptionModal
                    descriptionExample={descriptionExample}
                    setShowDescriptionModal={setShowDescriptionModal}
                    handleDescriptionChange={handleDescriptionChange}
                />
            )}
            <div className="mb-8">
                <button
                    disabled={isSubmitting}
                    onClick={async () => {
                        setIsSubmitting(true)
                        try {
                            await handlePost(file)
                        } catch (error) {
                            setIsSubmitting(false)
                        }
                    }}
                    className={`
                        active:bg-green-800
                        disabled:cursor-not-allowed disabled:bg-gray-500
                        w-full h-[60px] mb-8 bg-green-900  text-[#fff] rounded-md
                    `}
                >
                    {text.post}
                </button>
                {titleError && <div className="text-[12px] text-[#ff0000]">{text.titleError}</div>}
                {addressError && <div className="text-[12px] text-[#ff0000]">{text.addressError}</div>}
                {dateError && <div className="text-[12px] text-[#ff0000]">{text.dateError}</div>}
                {timeError && <div className="text-[12px] text-[#ff0000]">{text.timeError}</div>}
                {descriptionError && <div className="text-[12px] text-[#ff0000]">{text.descriptionError}</div>}
            </div>
        </div>
    )
}
