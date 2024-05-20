'use client'

import React from 'react'
import { container } from '@/app/common'
import { Board } from '@/app/types/board'
import { LocaleUpdateBoard, Locales } from '@/app/types/locales'
import { FileChange, BoardUpdateState } from '@/app/hooks'
import { AiOutlineSearch } from 'react-icons/ai'
import { ChooseImageFile, ChooseWalkDates, ChooseWalkTimes, DescriptionModal, DescriptionTextarea } from '.'

interface Props {
    lang: Locales
    text: LocaleUpdateBoard
    board: Board
    fileChangeState: FileChange
    boardState: BoardUpdateState
}

export function Mobile({ lang, text, board, fileChangeState, boardState }: Props) {
    const { file, fileSizeError, handleFileChange } = fileChangeState
    const {
        days,
        times,
        title,
        description,
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
        changeDayToKorean,
        changeTimeToKorean,
        handleDescriptionChange,
        setShowDescriptionModal,
        handleUpdate
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
                    <ChooseImageFile image={board?.image} file={file} handleFileChange={handleFileChange} />
                    {fileSizeError && <div className="text-[12px] text-[#ff0000]">{text.fileSizeError}</div>}
                </div>
                <div className="mb-8">
                    <div className="mb-4 text-[16px] font-[500]">2. {text.paragraph2}</div>
                    <button className="relative w-full max-w-[480px] h-[40px] pr-8 text-[16px] text-start border border-gray-300 rounded-md overflow-hidden text-ellipsis whitespace-nowrap bg-white">
                        <AiOutlineSearch className="absolute top-1/2 right-[4px] translate-y-[-50%] text-gray-400 text-[24px]" />
                    </button>
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
                    onClick={() => handleUpdate(file, board.id)}
                    className={`
                        w-full h-[60px] bg-green-900  text-[#fff] rounded-md
                        active:bg-green-800 mb-8
                    `}
                >
                    {text.update}
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
